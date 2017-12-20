package com.aiiju.dao.oa.permission;

import java.util.List;
import java.util.Map;

import com.aiiju.bean.oa.permission.Menu;
import com.aiiju.bean.oa.permission.MenuTree;

/**
 * @description 菜单Dao
 * @author 琪琪
 * @date 2016年10月18日 上午11:02:23 
 */
public interface IMenuDao {
  /**
   * 根据角色Id获取菜单
   * @param param
   * @return
   */
  List<Menu> getMenusByRoleId(Map<String,Object> param);
  
  /**
   * 获取全部菜单
   * @param params
   * @return
   */
  List<MenuTree> getAllMenu(Map<String, Object> params);
}
