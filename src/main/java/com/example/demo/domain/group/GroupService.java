package com.example.demo.domain.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public boolean groupExistsByGroupName(String groupName) {
        if(groupRepository.selectGroupByGroupName(groupName) > 0) {
            return true;
        }
        return false;
    }

}
