package com.liberty.poker.userstory;


import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user-story")
public class UserStoryController {

    private final ConversionService conversionService;
    private final AddUserStory addUserStory;
    private final RemoveUserStory removeUserStory;


    public UserStoryController(final ConversionService conversionService,
                               final AddUserStory addUserStory,
                               final RemoveUserStory removeUserStory) {
        this.conversionService = conversionService;
        this.addUserStory = addUserStory;
        this.removeUserStory = removeUserStory;
    }

    @PostMapping
    public ResponseEntity<UserStoryResponse> create(@RequestBody final UserStoryRequest userStoryRequest){
        final var userStory = addUserStory.execute(conversionService.convert(userStoryRequest, UserStory.class));
        final var userStoryResponse = conversionService.convert(userStory, UserStoryResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userStoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> destroy(@PathVariable final UUID id){

        removeUserStory.execute(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
