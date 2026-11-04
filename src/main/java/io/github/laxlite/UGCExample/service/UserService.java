package io.github.laxlite.UGCExample.service;

import io.github.laxlite.UGCExample.dto.BestAuthorDTO;
import io.github.laxlite.UGCExample.dto.CommentDTO;
import io.github.laxlite.UGCExample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    // TODO: уточнить параметры
    // groupNumber - номер пачки
    // authorsCount - общее количество показываемых групп
    // hours - за какое время находятся комментарии
    public List<BestAuthorDTO> getPopularAuthorsByRating(int groupsCount, int authorsPerGroup, int groupNumber, int hours){
        if (groupsCount <= 0 || authorsPerGroup <= 0 || groupNumber < 0 || hours <= 0) {
            throw new IllegalArgumentException(
                    "groupsCount, authorsPerGroup, hours must be > 0; groupNumber must be >= 0");
        }
        PageRequest pageRequest = PageRequest.of(groupNumber, authorsPerGroup);
        Instant threshold = Instant.now().minus(Duration.ofHours(hours));

        return userRepository.findByCreatedAtAfterOrderByRating(threshold, pageRequest)
                .stream()
                .map(user -> new BestAuthorDTO(
                        user.getId(), user.getNickname(), user.getRating()
                ))
                .toList();
    }

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
