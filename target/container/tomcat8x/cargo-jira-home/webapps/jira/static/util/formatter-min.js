define("jira/util/formatter",["exports","jira/util/data/meta"],function(t,r){"use strict";function e(){return AJS.I18n.getText.apply(AJS.I18n,arguments)}function a(t){var e=r.get("user-locale-group-separator")||"";return t.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g,e)}var n=AJS.format.bind(AJS);t.format=n,t.formatNumber=a,t.formatText=n,t.I18n={},t.I18n.getText=e});