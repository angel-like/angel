-- 菜单SQL
INSERT INTO sys_menu (parent_id, name, url, perms, type, icon, order_num)
    VALUES ('633', '开元血流成河', 'gamerecordxueliuchenghe/gamerecordxueliuchenghe', 'gamerecordkaiyuan:gamerecordkaiyuan:xlchlist', '1', 'config', '100');
    set @parentIdrole = @@identity;
INSERT INTO sys_role_menu ( role_id, menu_id)
SELECT role_id,@parentIdrole from sys_role_menu
where menu_id = 633;