package io.github.laxlite.UGCExample.service;

import io.github.laxlite.UGCExample.dto.TopicDTO;
import io.github.laxlite.UGCExample.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public List<TopicDTO> getAllTopics(){
        return topicRepository.findAll().stream().map(
                topic -> new TopicDTO(topic.getId(), topic.getSlug(), topic.getName())
                )
                .toList();
    }

    @Autowired
    public TopicService(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }
}
