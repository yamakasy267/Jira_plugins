define('jira/share/entities/display', [], function () {
    'use strict';

    /**
     * Object that contains the state of a share that needs to be rendered.
     *
     * @param display the HTML code that should render the component.
     * @param singleton is the share a singleton or not.
     * @param permission the underlying permission associated with the share.
     */

    function Display(display, description, singleton, permission) {
        this.display = display;
        this.singleton = singleton;
        this.permission = permission;
        this.description = description;
    }

    return Display;
});