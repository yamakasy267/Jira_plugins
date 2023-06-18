AJS.test.require("jira.webresources:browseprojects",function(){require(["jira/project/browse/projecttypesservice"],function(e){module("jira/project/browse/projecttypesservice",{setup:function(){e.init(null)},initServiceWithProjectType:function(t,i){var n={};n[t]={icon:i},n["jira-project-type-inaccessible"]={icon:"inaccessible-type-icon"},e.init(n)}}),test("Indicates that project types is not enabled when it is not initialized",function(){equal(e.areProjectTypesEnabled(),!1)}),test("Indicates that project types is not enabled when it is initialized with a valid object",function(){e.init(null),equal(e.areProjectTypesEnabled(),!1),e.init(void 0),equal(e.areProjectTypesEnabled(),!1),e.init(0),equal(e.areProjectTypesEnabled(),!1),e.init(!1),equal(e.areProjectTypesEnabled(),!1)}),test("Indicates that project types are enabled when it is initialized with an object containing the project types",function(){this.initServiceWithProjectType("business","business-icon"),equal(e.areProjectTypesEnabled(),!0)}),test("Returned icon is null when project types are disabled",function(){var t=e.getProjectTypeIcon("business");strictEqual(t,null)}),test("Returned icon is the one for inaccessible project type if type is unknown",function(){this.initServiceWithProjectType("business","business-icon");var t=e.getProjectTypeIcon("unknown-type");equal(t,"inaccessible-type-icon")}),test("Returned icon is the expected one for a project type that is known",function(){this.initServiceWithProjectType("business","business-icon");var t=e.getProjectTypeIcon("business");equal(t,"business-icon")})})});