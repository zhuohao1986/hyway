/*package cn.taroco.rbac.admin.controller;

import java.util.Map;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.way.admin.BaseController;
import com.way.system.api.SysOauthClientDetailsService;

import cn.taroco.rbac.admin.model.entity.SysOauthClientDetails;

*//**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 
 * @since 2018-05-15
 *//*
@RestController
@RequestMapping("/client")
public class OauthClientDetailsController extends BaseController {

    @Autowired
    private SysOauthClientDetailsService sysOauthClientDetailsService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    *//**
     * 通过ID查询
     *
     * @param id ID
     * @return SysOauthClientDetails
     *//*
    @GetMapping("/{id}")
    public SysOauthClientDetails get(@PathVariable Integer id) {
        return sysOauthClientDetailsService.selectById(id);
    }


    *//**
     * 分页查询信息
     *
     * @param params 分页对象
     * @return 分页对象
     *//*
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        return sysOauthClientDetailsService.selectPage(new Query<>(params), new EntityWrapper<>());
    }

    *//**
     * 添加
     *
     * @param client 实体
     * @return success/false
     *//*
    @PostMapping
    public Response add(@RequestBody SysOauthClientDetails client) {
        if (StringUtils.isEmpty(client.getAdditionalInformation())) {
            client.setAdditionalInformation(null);
        }
        final String secret = encoder.encode(client.getClientSecret());
        client.setClientSecret(secret);
        return Response.success(sysOauthClientDetailsService.insert(client));
    }

    *//**
     * 删除
     *
     * @param id ID
     * @return success/false
     *//*
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable String id) {
        SysOauthClientDetails sysOauthClientDetails = new SysOauthClientDetails();
        sysOauthClientDetails.setClientId(id);
        return Response.success(sysOauthClientDetailsService.deleteById(sysOauthClientDetails));
    }

    *//**
     * 编辑
     *
     * @param client 实体
     * @return success/false
     *//*
    @PutMapping
    public Response edit(@RequestBody SysOauthClientDetails client) {
        final String pass = client.getClientSecret();
        final SysOauthClientDetails details = sysOauthClientDetailsService.selectById(client.getClientId());
        if (encoder.matches(pass, details.getClientSecret())) {
            client.setClientSecret(encoder.encode(pass));
        }
        return Response.success(sysOauthClientDetailsService.updateById(client));
    }
}
*/