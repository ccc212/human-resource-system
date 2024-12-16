package com.hrsys.controller.user;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.constant.JwtClaimsConstant;
import com.hrsys.config.properties.JwtProperties;
import com.hrsys.constant.MessageConstant;
import com.hrsys.pojo.dto.UserAssignRoleDTO;
import com.hrsys.pojo.dto.UserLoginDTO;
import com.hrsys.pojo.dto.UserRegisterDTO;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.entity.User;
import com.hrsys.pojo.vo.UserLoginVO;
import com.hrsys.pojo.vo.UserVO;
import com.hrsys.service.IUserService;
import com.hrsys.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-21
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "用户管理")
@Validated
public class UserController {

    private final IUserService userService;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result<UserLoginVO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        User user = userService.login(userLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ID, user.getUserId());
        claims.put("userName", user.getUsername());
        claims.put("roleId", user.getRoleId());

        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );

        return Result.success(new UserLoginVO(user.getUserId(), token));
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result<?> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return Result.success(MessageConstant.REGISTER_SUCCESS);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户登出")
    public Result<?> logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        JwtUtil.invalidateToken(token);
        return Result.success(MessageConstant.LOGOUT_SUCCESS);
    }

    @PostMapping("/assignRole")
    @ApiOperation(value = "分配角色")
    public Result<?> assignRole(@RequestBody @Valid UserAssignRoleDTO userAssignRoleDTO) {
        userService.assignRole(userAssignRoleDTO);
        return Result.success(MessageConstant.ASSIGN_ROLE_SUCCESS);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Integer id) {
        userService.removeUser(id);
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表")
    public Result<List<UserVO>> list(@RequestParam(defaultValue = "1") Integer current,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<>(current, pageSize);
        Page<User> result = userService.page(page);
        // 封装成 UserVO
        List<UserVO> userVOList = result.getRecords().stream().map(user -> UserVO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .roleId(user.getRoleId())
                .build()).toList();
        return Result.success(userVOList);
    }

}
