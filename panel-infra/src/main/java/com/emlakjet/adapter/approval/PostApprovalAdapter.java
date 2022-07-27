package com.emlakjet.adapter.approval;

import com.emlakjet.adapter.post.PostMapper;
import com.emlakjet.adapter.post.repo.PostRepository;
import com.emlakjet.approval.port.PostApprovalPort;
import com.emlakjet.approval.usecase.PostApprovalUseCase;
import com.emlakjet.post.exception.PostNotFoundException;
import com.emlakjet.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // TODO: Service kullanılmış diğer projelerde
@RequiredArgsConstructor
public class PostApprovalAdapter implements PostApprovalPort {

    private final PostRepository postRepository;

    private final PostMapper mapper;

    @Override
    public Post updateApprovalStatus(PostApprovalUseCase useCase) {

        var postFound = postRepository.findById(useCase.postId())
                .map(post -> post.toBuilder()
                        .approvalStatus(useCase.approvalStatus())
                        .build())
                .orElseThrow(PostNotFoundException::new);

        return mapper.toPost(postRepository.save(postFound));

    }
}
