package com.cometogo.ctg.group.controller;


import com.cometogo.ctg.group.dto.GroupDetailDto;
import com.cometogo.ctg.group.dto.GroupDto;
import com.cometogo.ctg.group.dto.MyGroupDto;
import com.cometogo.ctg.group.service.GroupService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/groups")
public class GroupController {


    private final GroupService groupService;



    public GroupController(GroupService groupService) {
        this.groupService = groupService;

    }


    /** 🔹 동호회 생성 폼 */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("groupDto", new GroupDto());
        return "groups/create";
    }

    /** 🔹 동호회 생성 처리 */
    @PostMapping("/create")
    public String createGroup(@ModelAttribute @Valid GroupDto groupDto,
                              BindingResult bindingResult,
                              HttpSession session) {

        // 유효성 검증
        if (bindingResult.hasErrors()) {
            return "groups/create";
        }

        Long ownerUserId = (Long) session.getAttribute("user_id");
        if (ownerUserId == null) {
            return "redirect:/user/login";
        }

        groupDto.setOwnerUserId(ownerUserId);

        // 그룹 생성 후 생성된 ID 반환
        Long createdGroupId = groupService.createGroup(groupDto);

        // 생성 후 상세 페이지로 리다이렉트
        return "redirect:/groups/" + createdGroupId + "/detail";
    }

    @GetMapping("/groups/{groupId}")
    public String groupDetail(@PathVariable Long groupId,
                              Model model,
                              HttpSession session) {

        Long loginUserId = (Long) session.getAttribute("user_id");
        GroupDetailDto groupDetail = groupService.getGroupDetailById(groupId, loginUserId);

        if (groupDetail == null) {
            return "error/404";
        }

        // 🔥 여기 두 줄이 반드시 필요!!
        model.addAttribute("isOwner", groupDetail.isOwner());
        model.addAttribute("isMember", groupDetail.isMember());

        model.addAttribute("groupDetail", groupDetail);

        return "groups/detail";
    }


    @GetMapping("/{groupId}/detail")
    public String showGroupDetail(@PathVariable("groupId") Long groupId,
                                  Model model,
                                  HttpSession session) {

        // 로그인된 사용자 ID 가져오기
        Long userId = (Long) session.getAttribute("user_id");

        // 서비스 호출 (userId를 넘겨서 DTO 내부 isOwner/isMember 세팅)
        GroupDetailDto groupDetail = groupService.getGroupDetail(groupId, userId);
        if (groupDetail == null) {
            return "error/404";
        }

        // DTO에 이미 isOwner/isMember가 있으므로 그대로 모델에 넣기
        model.addAttribute("groupDetail", groupDetail);
        model.addAttribute("isOwner", groupDetail.isOwner());
        model.addAttribute("isMember", groupDetail.isMember());

        return "groups/detail";
    }



    @GetMapping("/search")
    public String searchGroups(@RequestParam(required = false) String region,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String category,
                               @RequestParam(required = false) String sort,
                               Model model) {

        List<GroupDetailDto> groups = groupService.searchGroups(region, keyword, category, sort);

        model.addAttribute("groups", groups);
        model.addAttribute("region", region);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("sort", sort);

        return "groups/search";
    }


    /** ✅ 마이페이지 - 내가 가입한 동호회 목록 */
    @GetMapping("/mygroup")
    public String myGroups(Model model, HttpSession session) {
        System.out.println("세션 userId:" + session.getAttribute("user_id"));
        Long userId = (Long) session.getAttribute("user_id");

        if (userId == null) {
            System.out.println("세션이없습니다 ");
            return "redirect:/user/login";
        }

        List<MyGroupDto> myGroups = groupService.getMyGroups(userId);
        model.addAttribute("myGroups", myGroups);

        return "mygroup"; // ✅ 뷰 파일 (templates/groups/mygroup.html)

    }

    @GetMapping("/map")
    public String mapPage() {
        return "groups/map"; // => templates/map.html
    }
}



