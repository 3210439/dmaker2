package com.fastcampus.programming.dmaker.controller;

// 사용자의 입력이 최초로 받아지는 위치

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.dto.EditDeveloper;
import com.fastcampus.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    // entity를 그대로 리턴하는 것은 불필요한 정보를 제공하거나 트랜잭션이 없는 상태에서 호출하게 되면 않좋은 결과가 나타날 수 있다.
    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        //Get /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getAllDevelopers(
            @PathVariable String memberId
    ) {
        //Get /developers HTTP/1.1
        log.info("GET /developers detail");

        return dMakerService.getDeveloperDetail(memberId);
    }


    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
    ) {
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);

    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request
    ) {
        log.info("editDeveloper");

        return dMakerService.editDeveloper(memberId, request);
    }
}
