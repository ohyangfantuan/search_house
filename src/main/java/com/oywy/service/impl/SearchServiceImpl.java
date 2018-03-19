package com.oywy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.oywy.entity.House;
import com.oywy.entity.HouseDetail;
import com.oywy.entity.HouseTag;
import com.oywy.mapper.HouseDetailMapper;
import com.oywy.mapper.HouseMapper;
import com.oywy.mapper.HouseTagMapper;
import com.oywy.service.SearchService;
import com.oywy.service.search.HouseIndexKey;
import com.oywy.service.search.HouseIndexTemplate;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oywy on 2018/3/18.
 */
@Service
public class SearchServiceImpl implements SearchService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private HouseDetailMapper houseDetailMapper;
    @Autowired
    private HouseTagMapper houseTagMapper;
    @Autowired
    private TransportClient esClient;

    private final String INDEX_NAME = "searchhouse";
    private final String INDEX_TYPE = "house";

    @Override
    public void index(Long houseId) {
        House house = houseMapper.selectById(houseId);
        if (house == null) {
            logger.error("Index house {} does not exists", houseId);
            return;
        }
        HouseIndexTemplate indexTemplate = new HouseIndexTemplate();
        BeanUtil.copyProperties(house, indexTemplate);

        HouseDetail houseDetail = houseDetailMapper.selectList(
                new EntityWrapper<HouseDetail>()
                        .eq("house_id", houseId)).get(0);
        BeanUtil.copyProperties(houseDetail, indexTemplate);

        List<HouseTag> houseTags = houseTagMapper.selectList(
                new EntityWrapper<HouseTag>()
                        .eq("house_id", houseId));
        if (CollUtil.isNotEmpty(houseTags)) {
            List<String> tags = houseTags.stream()
                    .map(houseTag -> houseTag.getName())
                    .collect(Collectors.toList());
            indexTemplate.setTags(tags);
        }

        SearchRequestBuilder requestBuilder = esClient.prepareSearch(INDEX_NAME).setTypes(INDEX_TYPE)
                .setQuery(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, houseId));

        logger.debug(requestBuilder.toString());

        SearchResponse searchResponse = requestBuilder.get();

        boolean success;
        long totalHits = searchResponse.getHits().getTotalHits();
        if (totalHits == 0)
            success = create(indexTemplate);
        else if (totalHits == 1) {
            String id = searchResponse.getHits().getAt(0).getId();
            success = update(id, indexTemplate);
        } else
            success = deleteAndCreate(totalHits, indexTemplate);
        if (success)
            logger.debug("index success:" + houseId);

    }

    private boolean create(HouseIndexTemplate indexTemplate) {
        IndexResponse response = esClient.prepareIndex(INDEX_NAME, INDEX_TYPE)
                .setSource(BeanUtil.beanToMap(indexTemplate), XContentType.JSON)
                .get();
        return response.isCreated();
    }

    private boolean update(String esId, HouseIndexTemplate indexTemplate) {
        UpdateResponse response = esClient.prepareUpdate(INDEX_NAME, INDEX_TYPE, esId)
                .setDoc(BeanUtil.beanToMap(indexTemplate), XContentType.JSON)
                .get();
        return response.isCreated();
    }

    private boolean deleteAndCreate(long totalHit, HouseIndexTemplate indexTemplate) {
//        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE.newRequestBuilder(esClient)
//                .filter(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, indexTemplate.getHouseId()))
//                .source(INDEX_NAME);
//        logger.debug("Delete by query for house:" + builder);
//        BulkByScrollResponse response = builder.get();
//        long deleted = response.getDeleted();
//        if (deleted != totalHit) {
//            logger.warn("need delete {},but {} was deleted", totalHit, deleted);
//            return false;
//        }
//        return create(indexTemplate);
        return true;

    }

    @Override
    public void remove(Long houseId) {
        SearchResponse response = esClient.prepareSearch(INDEX_NAME).setTypes(INDEX_TYPE)
                .setQuery(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, houseId)).get();
        String id = response.getHits().getAt(0).getId();
        DeleteResponse deleteResponse = esClient.prepareDelete(INDEX_NAME, INDEX_TYPE, id).get();
        System.out.println(deleteResponse.isFound());
    }
}
