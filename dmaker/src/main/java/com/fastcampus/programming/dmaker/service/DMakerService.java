package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.Optional;

import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final EntityManager em;
    // ACID
    // Atomic : 원자성 a라는 사람이 1만원이 빠져나가고 b라는 사람의 계좌로 1만원이 들어간다.
    // a라는 사람이 돈을 보낸후 에러가나면 돈을 다시 돌려둔다.
    // Consistency : 일관성 트랜잭션이 끝나는 지점에서는 일관성이 맞춰줘 있어야한다.
    // 내 계좌에서 100억이 없는데 100억이상 보낼 수 없다.
    // Isolation : 고립성 성능과 trade off 관계 한 시점에 한 트랜잭션 처리 시리얼라이즈 적정하게 설정해야한다.
    // Durability : 지속성 커밋이 된 시점에서 이력은 남아있어야 한다. 모든 이력을 남긴다.
    @Transactional
    public void createDeveloper(CreateDeveloper.Request request){
        validateCreateDeveloperRequest(request);
        EntityTransaction transaction = em.getTransaction();
        //try{
                transaction.begin();
                // business logic start
                Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .name("Olaf")
                .age(5)
                .build();

            developerRepository.save(developer);
          //  transaction.commit();
        //} catch (Exception e){
          //  transaction.rollback();
            //throw e;
        //}
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request){
        // business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();
        if(developerLevel == DeveloperLevel.SENIOR
        && experienceYears < 10){
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if (developerLevel == DeveloperLevel.JUNGNIOR
        && ( experienceYears < 4 || experienceYears > 10)){
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4){
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

      //  Optional<Developer> developer = developerRepository.findByMemberId(request.getMemberId());
      //  if(developer.isPresent())
      //      throw new DMakerException(DUPLICATED_MEMBER_ID);

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));
    }
}
