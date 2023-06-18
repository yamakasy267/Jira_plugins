define("jira/share/entities/share-type/user-share",["jira/share/entities/display","jira/share/entities/share-permission","jira/share/i18n","jquery"],function(e,t,i,s){"use strict";function r(e){this.type="user",this.singleton=!1,this.parentElement=document.querySelector("."+e),this.value=null,this.singleSelectController=null,this.dirty=!1}return r.prototype={initialise:function(){var e=this;this.userSelect=this.getSubElement("user-share-select"),this.userSelect&&s(this.userSelect).on("selected",function(t,i,s){e.value=i.properties?i.properties.userKey:i.value(),e.userSelect.selectedOptions&&(r.lastOption=e.userSelect.selectedOptions[0]),e.dirty=!0,e.singleSelectController||(e.singleSelectController=s)})},getDisplayDescriptionFromUI:function(){return""},getDisplayFromUI:function(i){var s=null;if(this.singleSelectController&&this.value){i||this.singleSelectController.clear();var r=new t(this.type,this.value,null);s=new e(this.getDisplayString(this.value),this.getDescriptionString(this.value,!0),this.singleton,r)}return s},inputChangesExist:function(){return this.dirty},getDisplayString:function(e){var t=i.getMessage("share_user_display");return i.formatMessage(t,e)},getDescriptionString:function(e,t){var s=i.getMessage("share_user_description");return t?i.formatMessageUnescaped(s,e):i.formatMessage(s,e)},getDisplayFromPermission:function(i){if(!i||i.type!==this.type||!i.param1)return null;var s=new t(this.type,i.param1,null);return new e(this.getDisplayString(s.param1),this.getDescriptionString(i.param1,!0),this.singleton,s)},getDisplayWarning:function(){return""},getSubElement:function(e){return this.parentElement.querySelector("."+e)},updateSelectionFromPermission:function(e){var t=r.lastOption;t&&e.param1===t.value&&this.userSelect.append(t)}},r});