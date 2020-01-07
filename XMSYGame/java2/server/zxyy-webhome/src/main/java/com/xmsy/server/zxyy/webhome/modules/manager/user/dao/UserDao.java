package com.xmsy.server.zxyy.webhome.modules.manager.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.manager.agent.param.Agent;
import com.xmsy.server.zxyy.webhome.modules.manager.rankinglistday.entity.RankingListDayEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserPasswordResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
    /**
     * 新增用户并返回用户ID
     *
     * @param userEntity
     */
    void insertGetId(UserEntity userEntity);

    /**
     * 查询用户集合 根据账号列表
     *
     * @param accountList 账号列表
     * @return
     */
    List<UserEntity> findUserListByAccount(@Param("accountList") String[] accountList);

    /**
     * 查询用户集合 根据userId列表
     *
     * @param idList
     * @return
     */
    List<UserEntity> findUserListByIdList(@Param("idList") Collection<Long> idList);

    /**
     * 查询用户集合 根据层级列表
     *
     * @param hierarchyIDs 层级列表
     * @return
     */
    List<UserEntity> findUserListByHierarchy(@Param("hierarchyIDs") String[] hierarchyIDs);

    List<UserEntity> findUserListByTouristId(@Param("touristId") String touristId);


    /**
     * 会员管理页面分页查询
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> findUserPage(@Param("user") UserParam user, Pagination page);

    /**
     * 会员管理页面分页查询(根据最近充值)
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> findUserPageForRecharge(@Param("user") UserParam user, Pagination page);

    /**
     * 会员管理页面分页查询(根据充值总额)
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> findUserPageForRechargeNumOrder(@Param("user") UserParam user, Pagination page);

    /**
     * 会员管理页面分页查询(根据最近取款)
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> findUserPageForTakeMoney(@Param("user") UserParam user, Pagination page);

    /**
     * 会员管理页面分页查询(根据取款总额)
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> findUserPageForTakeMoneyOrderNum(@Param("user") UserParam user, Pagination page);

    /**
     * 会员管理页面分页查询
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> findRiskUserPage(@Param("user") UserParam user, Pagination page);

    /**
     * 会员管理页面
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> findUserList(@Param("user") UserParam user);

    /**
     * 会员管理页面计算有多少条
     *
     * @param param
     * @return
     */
    Integer queryTotal(Map<String, Object> param);

    /**
     * 删除会员的银行卡信息
     *
     * @param userId
     * @return
     */
    Integer deleteBankInfo(@Param("userId") Long userId);

    /**
     * 修改备注
     *
     * @param userEntity
     * @return
     */
    Integer updateUserBaseInfo(UserEntity userEntity);

    /**
     * 根据ID修改用户余额，金币，佣金
     *
     * @param userEntity
     * @return
     */
    Integer updateUserWalletByUserId(UserEntity userEntity);


    Integer freezeUserCoinByUserId(@Param("user") UserEntity userEntity);
    /**
     * 代理列表
     *
     * @param user 用户信息
     * @return
     */
    List<Agent> getAgentList(Pagination page, @Param("user") UserEntity user);

    /**
     * 代理列表
     *
     * @param 代理下级列表
     * @return
     */
    List<UserEntity> getAgentSubordinateList(Pagination page, @Param("account") String account,
                                             @Param("userId") Long userId);

    /**
     * 试玩账号列表 包含删除
     *
     * @return
     */
    List<UserEntity> getTrialUserList();

    Integer countUserByAccount(@Param("account") String account);

    /**
     * 修改删除状态为0
     *
     * @param id
     * @return
     */
    Integer updateUserDeleteStatus(@Param("id") long id);

    /**
     * .用户密码信息
     *
     * @param id
     * @return
     */
    UserPasswordResult getUserPasswordInfo(@Param("id") long id);

    /**
     * 统计会员数量，有效会员数量(有充值)====非试玩账号
     *
     * @param
     * @return
     */
    Map<String, Object> getStatisticsNumber();

    /**
     * 通过时间获取新增会员数,有效会员数
     *
     * @return
     */
    Map<String, Object> sumUserForDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 新增会员数近7日
     *
     * @param
     * @return
     */
    List<Map<String, Object>> getInsertUserNumber();

    /**
     * 按照日期范围统计余额
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<RankingListDayEntity> statisticsWealthByDay();

    /**
     * 修改用户是否首冲状态
     */
    void updateUserFirstRecharge(Long id);

    /**
     * 逻辑删除用户信息
     *
     * @param id
     * @return
     */
    Integer deleteUserById(Long id);

    Long statisticsRiskNum();
	/**
	 * 通过用户id  修改用户上级id
	 * @param userId
	 * @param superiorsId
	 */
	Integer updateSuperiorsId(@Param("userId") Long userId,@Param("superiorsId") Long superiorsId);

    /**
     * 根据账号和用户手机号查询用户
     *
     * @param account 账号
     * @param phone   手机号
     * @return List<UserEntity>
     */
    List<UserEntity> findUserByAccountOrPhone(@Param("account") String account, @Param("phone") String phone);
    /**
	 * Gm账号id列表
	 * @return
	 */
    List<Long> getGmUser();

    List<UserEntity> findUserListByPhone(@Param("phone") String phone);

    Integer batchUpgradeUserAccount(@Param("userId") Long userId,@Param("account")String account);

    void unfreezeUserCoin(@Param("user") UserEntity entity);
}
