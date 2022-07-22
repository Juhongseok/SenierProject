package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.repository.MapRepository;
import com.jhs.seniorProject.repository.SmallSubjectRepository;
import com.jhs.seniorProject.repository.UserRepository;
import com.jhs.seniorProject.service.requestform.AddSubjectDto;
import com.jhs.seniorProject.service.responseform.SmallSubjectInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class SmallSubjectService {

    private final SmallSubjectRepository smallSubjectRepository;
    private final MapRepository mapRepository;
    private final UserRepository userRepository;

    public void updateName(Long subjectId, String changeName) {
        SmallSubject findSubject = smallSubjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException());

        findSubject.changeSubjectName(changeName);
    }

    public Long addSubject(AddSubjectDto addSubjectDto) {
        User user = userRepository.findById(addSubjectDto.getUserId()).get();
        Map map = mapRepository.findById(addSubjectDto.getMapId()).get();
        SmallSubject smallSubject = new SmallSubject(addSubjectDto.getSubjectName(), map, user.getId());
        smallSubjectRepository.save(smallSubject);
        return map.getId();
    }

    public List<SmallSubjectInfo> getSubjects(Long mapId){
        Map map = mapRepository.findById(mapId).get();
        return map.getSmallSubjects().stream()
                .map(sb -> new SmallSubjectInfo(sb.getId(), sb.getSubjectName()))
                .collect(toList());
    }
}
