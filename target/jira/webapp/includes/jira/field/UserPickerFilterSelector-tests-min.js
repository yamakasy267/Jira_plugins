AJS.test.require(["jira.webresources:user-picker-filter-configuration-resources"],function(){var e=require("jquery"),r=require("underscore"),o=require("jira/admin/custom-fields/user-picker-filter/selector-panel");module("JIRA.Admin.CustomFields.UserPickerFilter",{setup:function(){this.sandbox=sinon.sandbox.create();var r=e("#qunit-fixture");this.$selectorPanel=e('<div id="selector-panel">').appendTo(r)},teardown:function(){this.sandbox.restore()},_createProjectRole:function(e,r,o){return{id:e,name:r,description:o}},_createGroup:function(e){return{name:e}},initPanelData:function(e){return o._initData(e,[this._createProjectRole(1e4,"prj1","desc 1")]),o},initPanel:function(e){return o.initialize(this.$selectorPanel,e,this.getDefaultGroups(),this.getDefaultProjectRoles()),o},getDefaultProjectRoles:function(){return[this._createProjectRole(10001,"prj role 1","desc 1"),this._createProjectRole(10002,"prj role 2","desc 2"),this._createProjectRole(10003,"prj role 3","desc 3")]},getDefaultGroups:function(){return[this._createGroup("group 1"),this._createGroup("group 2"),this._createGroup("group 3")]},filter:function(e,o,s){return r.extend({enabled:e},r.isArray(s)?{groups:s}:{},r.isArray(o)?{roleIds:o}:{})},selectAndAdd:function(e,r,o){e._getTypeSelector().val(r).change(),"group"===r?e._getGroupSelector().val(o).change():e._getRoleSelector().val(o).change(),e._getAddFilterIcon().click()},addGroup:function(e,r){this.selectAndAdd(e,"group",r)},addRole:function(e,r){this.selectAndAdd(e,"role",r)},remove:function(e,r,o){this.$selectorPanel.find(".filter-entry[data-type='"+r+"'][data-value='"+o+"']").find(".delete-filter").click()},removeGroup:function(e,r){this.remove(e,"group",r)},removeRole:function(e,r){this.remove(e,"role",r)}}),test("json updater adds new role and returns true",function(){var e=this.initPanelData({enabled:!0});ok(e._updateJson("role","add","123"),"adding new role id returns true");var o=e.getUserFilter();ok(r.contains(o.roleIds,123),"new role id is added into user filter")}),test("json updater adds existing role and returns false",function(){var e=this.initPanelData({enabled:!0,roleIds:[123]});ok(!e._updateJson("role","add","123"),"adding existing role id returns false");var o=e.getUserFilter();ok(r.contains(o.roleIds,123),"existing role id is still in user filter")}),test("json updater adds non integer role id should return false",function(){var e=this.initPanelData({enabled:!0,roleIds:[]});ok(!e._updateJson("role","add","a123b"),"adding invalid role id returns false");var o=e.getUserFilter();equal(r.size(o.roleIds),0,"no new role id is added")}),test("json updater removes existing role and returns true",function(){var e=this.initPanelData({enabled:!0,roleIds:[123]});ok(e._updateJson("role","remove","123"),"removing existing role id returns true");var o=e.getUserFilter();ok(!r.contains(o.roleIds,123),"old role id is removed into user filter")}),test("json updater removes non-existing role and returns false",function(){var e=this.initPanelData({enabled:!0,roleIds:[456]});ok(!e._updateJson("role","remove","123"),"removing non-existing role id returns false");var o=e.getUserFilter();ok(r.contains(o.roleIds,456),"existing role id is still in user filter")}),test("json updater adds new group and returns true",function(){var e=this.initPanelData({enabled:!0});ok(e._updateJson("group","add","g1"),"adding new group returns true");var o=e.getUserFilter();ok(r.contains(o.groups,"g1"),"new group is added into user filter")}),test("json updater adds existing group and returns false",function(){var e=this.initPanelData({enabled:!0,groups:["g1"]});ok(!e._updateJson("group","add","g1"),"adding existing group returns false");var o=e.getUserFilter();ok(r.contains(o.groups,"g1"),"existing group is still in user filter")}),test("json updater removes existing group and returns true",function(){var e=this.initPanelData({enabled:!0,groups:["g1"]});ok(e._updateJson("group","remove","g1"),"removing existing group returns true");var o=e.getUserFilter();ok(!r.contains(o.groups,"g1"),"old group is removed into user filter")}),test("json updater removes non-existing group and returns false",function(){var e=this.initPanelData({enabled:!0,groups:["g2"]});ok(!e._updateJson("group","remove","g1"),"removing non-existing group returns false");var o=e.getUserFilter();ok(r.contains(o.groups,"g2"),"existing group is still in user filter")}),test("copy filter with disabled",function(){var e=[[{enabled:!1},this.filter(!1,[],[]),"disabled filter"],[{enabled:!1,roleIds:[1],groups:["1"]},this.filter(!1,[],[]),"disabled filter reset other fields"],[{enabled:!0,roleIds:[1],groups:["1"]},this.filter(!0,[1],["1"]),"enabled filter"],[{enabled:!0,groups:["1","2","3"]},this.filter(!0,[],["1","2","3"]),"enabled filter without roles"],[{enabled:!0,roleIds:[1,2,3]},this.filter(!0,[1,2,3],[]),"enabled filter without groups"],[{enabled:!0,groups:["1","2","3"],roleIds:[1,2,3]},this.filter(!0,[1,2,3],["1","2","3"]),"enabled filter without groups"]],s=o;r.each(e,function(e){var o=e[0],t=s._copyUserFilter(o),i=e[1];deepEqual(t,i,e[2]),o.enabled=!o.enabled,r.size(o.roleIds)>0&&(o.roleIds[0]=12345),r.size(o.groups)>0&&(o.groups[0]="12345"),deepEqual(t,i,e[2]+" copied not changed")})}),test("adds groups from UI",function(){var e=this.initPanel({enabled:!0,groups:[]});this.addGroup(e,"group 1");var o=e.getUserFilter();ok(r.contains(o.groups,"group 1"),"group 1 is added into empty groups in filter successfully"),equal(r.size(o.roleIds),0,"roleIds not changed"),this.addGroup(e,"group 2"),o=e.getUserFilter(),ok(r.contains(o.groups,"group 2"),"group 2 is added into groups in filter successfully"),equal(r.size(o.roleIds),0,"roleIds not changed"),this.addGroup(e,"group 1"),o=e.getUserFilter(),equal(r.size(o.groups),2,"size of groups in filter is not changed after adding duplicated group"),ok(r.contains(o.groups,"group 1"),"existing groups 1 are still in filter"),ok(r.contains(o.groups,"group 2"),"existing groups 2 are still in filter"),equal(r.size(o.roleIds),0,"roleIds not changed"),this.addGroup(e,"group 3"),o=e.getUserFilter(),ok(r.contains(o.groups,"group 3"),"group 3 is added into groups in filter successfully"),equal(r.size(o.roleIds),0,"roleIds not changed")}),test("adds roles from UI",function(){var e=this.initPanel({enabled:!0,groups:[]});this.addRole(e,10001);var o=e.getUserFilter();ok(r.contains(o.roleIds,10001),"role 10001 is added into empty roleIds in filter successfully"),equal(r.size(o.groups),0,"groups not changed"),this.addRole(e,10002),o=e.getUserFilter(),ok(r.contains(o.roleIds,10002),"role 10002 is added into roleIds in filter successfully"),equal(r.size(o.groups),0,"groups not changed"),this.addRole(e,10001),o=e.getUserFilter(),equal(r.size(o.roleIds),2,"size of roles in filter is not changed after adding duplicated roleId"),ok(r.contains(o.roleIds,10001),"existing roles 10001 are still in filter"),ok(r.contains(o.roleIds,10002),"existing roles 10002 are still in filter"),equal(r.size(o.groups),0,"groups not changed"),this.addRole(e,10003),o=e.getUserFilter(),ok(r.contains(o.roleIds,10003),"role 10003 is added into roleIds in filter successfully"),equal(r.size(o.groups),0,"groups not changed")}),test("adds roles and groups from UI",function(){var e=this.initPanel({enabled:!0,groups:[],roleIds:[]});this.addRole(e,10001);var o=e.getUserFilter();ok(r.contains(o.roleIds,10001),"role 10001 is added into empty roleIds in filter successfully"),equal(r.size(o.groups),0,"groups not changed"),this.addGroup(e,"group 2"),o=e.getUserFilter(),ok(r.contains(o.roleIds,10001),"role 10001 is still in filter"),ok(r.contains(o.groups,"group 2"),"group 2 is added into filter successfully"),this.addGroup(e,"group 1"),o=e.getUserFilter(),ok(r.contains(o.roleIds,10001),"role 10001 is still in filter"),ok(r.contains(o.groups,"group 1"),"group 1 is added into filter successfully"),ok(r.contains(o.groups,"group 2"),"group 2 is still in filter"),this.addRole(e,10003),o=e.getUserFilter(),ok(r.contains(o.roleIds,10003),"role 10003 is added into filter successfully"),ok(r.contains(o.roleIds,10001),"role 10001 is still in filter"),equal(r.size(o.groups),2,"groups not changed"),this.addRole(e,10002),o=e.getUserFilter(),ok(r.contains(o.roleIds,10002),"role 10002 is added into filter successfully"),ok(r.contains(o.roleIds,10001),"role 10001 is still in filter"),ok(r.contains(o.roleIds,10003),"role 10003 is still in filter"),equal(r.size(o.groups),2,"groups not changed")}),test("remove groups from UI",function(){var e=this.initPanel({enabled:!0,groups:["group 1","group 2"]});this.removeGroup(e,"group 1");var o=e.getUserFilter();ok(!r.contains(o.groups,"group 1"),"group 1 is removed from filter successfully"),equal(r.size(o.roleIds),0,"roleIds not changed"),this.removeGroup(e,"group 1"),o=e.getUserFilter(),ok(r.contains(o.groups,"group 2"),"group 2 is not affected and still in filter"),ok(!r.contains(o.groups,"group 1"),"group 1 is not in filter"),equal(r.size(o.roleIds),0,"roleIds not changed"),this.addGroup(e,"group 3"),o=e.getUserFilter(),equal(r.size(o.groups),2,"size of groups in filter is increased after adding a new group"),ok(r.contains(o.groups,"group 3"),"groups 3 is added into filter successfully"),ok(r.contains(o.groups,"group 2"),"existing groups 2 are still in filter"),equal(r.size(o.roleIds),0,"roleIds not changed"),this.addGroup(e,"group 1"),o=e.getUserFilter(),ok(r.contains(o.groups,"group 1"),"group 1 is added back filter successfully"),equal(r.size(o.roleIds),0,"roleIds not changed")}),test("remove roles from UI",function(){var e=this.initPanel({enabled:!0,roleIds:[10001,10002]});this.removeRole(e,10001);var o=e.getUserFilter();ok(!r.contains(o.roleIds,10001),"role 10001 is removed from filter successfully"),equal(r.size(o.groups),0,"groups not changed"),this.removeRole(e,10001),o=e.getUserFilter(),ok(r.contains(o.roleIds,10002),"role 10002 is not affected and still in filter"),ok(!r.contains(o.roleIds,10001),"role 10001 is not in filter"),equal(r.size(o.groups),0,"groups not changed"),this.addRole(e,10003),o=e.getUserFilter(),equal(r.size(o.roleIds),2,"size of role in filter is increased after adding a new role"),ok(r.contains(o.roleIds,10003),"role 10003 is added into filter successfully"),ok(r.contains(o.roleIds,10002),"existing role 10002 are still in filter"),equal(r.size(o.groups),0,"groups not changed"),this.addRole(e,10001),o=e.getUserFilter(),ok(r.contains(o.roleIds,10001),"role 10001 is added back filter successfully"),equal(r.size(o.groups),0,"groups not changed")}),test("remove groups and roles from UI",function(){var e=this.initPanel({enabled:!0,groups:["group 1","group 2"],roleIds:[10001,10002]});this.removeRole(e,10001);var o=e.getUserFilter();ok(!r.contains(o.roleIds,10001),"role 10001 is removed from filter successfully"),equal(r.size(o.groups),2,"groups not changed"),this.removeGroup(e,"group 1"),o=e.getUserFilter(),ok(!r.contains(o.groups,"group 1"),"group 1 is removed from filter successfully"),equal(r.size(o.roleIds),1,"roleIds not changed"),this.removeGroup(e,"group 2"),o=e.getUserFilter(),ok(!r.contains(o.groups,"group 2"),"group 2 is removed from filter successfully"),ok(!r.contains(o.groups,"group 1"),"group 1 is not in filter"),equal(r.size(o.roleIds),1,"roleIds not changed"),this.removeRole(e,10002),o=e.getUserFilter(),ok(!r.contains(o.roleIds,10002),"role 10002 is removed from filter successfully"),ok(!r.contains(o.roleIds,10001),"role 10001 is not in filter"),equal(r.size(o.groups),0,"groups not changed"),this.addGroup(e,"group 3"),o=e.getUserFilter(),ok(r.contains(o.groups,"group 3"),"groups 3 is added into filter successfully"),equal(r.size(o.roleIds),0,"roleIds not changed"),this.addRole(e,10003),o=e.getUserFilter(),ok(r.contains(o.roleIds,10003),"role 10003 is added into filter successfully"),equal(r.size(o.groups),1,"groups not changed"),this.addRole(e,10001),o=e.getUserFilter(),ok(r.contains(o.roleIds,10001),"role 10001 is added back filter successfully"),equal(r.size(o.groups),1,"groups not changed"),this.addGroup(e,"group 1"),o=e.getUserFilter(),ok(r.contains(o.groups,"group 1"),"group 1 is added back filter successfully"),equal(r.size(o.roleIds),2,"roleIds not changed")})});