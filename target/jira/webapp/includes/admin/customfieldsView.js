/**
 * This view renders the view custom field page
 * It doesn't render a template own its own, instead binds to an existing element rendered from server.
 * It initializes the pagable collection for custom fields, fetches the data from server and renders the table view or empty message
 * depending on the number of custom fields
 */
define('jira/customfields/customfieldsView', ['jquery', 'jira/marionette-3.1', 'jira/customfields/customfieldsCollection', 'jira/customfields/customfieldsTableView', 'jira/customfields/customfieldsEmptyView', 'jira/message', 'jira/dialog/error-dialog'], function ($, Marionette, Customfields, CustomfieldTableView, CustomfieldEmptyView, Messages, ErrorDialog) {
    'use strict';

    return Marionette.View.extend({
        template: $.noop,
        initialize: function initialize() {
            this.collection = new Customfields();
            this.fetchData().done(this.render).fail(this.handleErrorResponse.bind(this));
        },

        ui: {
            container: '#customfields-container',
            progressIndicator: '#custom-fields-loading-indicator'
        },
        regions: {
            container: '@ui.container'
        },
        childViewEvents: {
            'navigate:start': 'displayLoadingIndicator',
            'navigate:end': 'hideLoadingIndicator',
            'navigate:error': 'handleErrorResponse',
            'search:start': 'displayLoadingIndicator',
            'search:end': 'hideLoadingIndicator'
        },
        onRender: function onRender() {
            if (this.collection.length) {
                this.showChildView('container', new CustomfieldTableView({
                    collection: this.collection
                }));
            } else {
                this.showChildView('container', new CustomfieldEmptyView());
            }
        },
        fetchData: function fetchData() {
            this.displayLoadingIndicator();
            return this.collection.getFirstPage().done(this.hideLoadingIndicator.bind(this));
        },
        displayLoadingIndicator: function displayLoadingIndicator() {
            this.getUI('container').addClass('active').spin('large');
        },
        hideLoadingIndicator: function hideLoadingIndicator() {
            this.getUI('container').removeClass('active').spinStop();
        },

        handleErrorResponse: function handleErrorResponse(errorObj) {
            var status = errorObj.status,
                responseText = errorObj.responseText;

            var messages = this._parseResponse(responseText);

            var message = JIRA.Templates.Admin.Customfields.applicationAccessError({
                messages: messages,
                status: status
            });

            switch (status) {
                case 401:
                case 403:
                    var heading = JIRA.Templates.Admin.Customfields.applicationAccessErrorHeading({
                        status: status
                    });
                    this._showErrorDialogue(message, heading);
                    break;
                default:
                    this._showErrorMessage(message);
                    break;
            }
        },

        _parseResponse: function _parseResponse(responseText) {
            try {
                var errors = JSON.parse(responseText);
                var errorMessages = errors.errorMessages,
                    message = errors.message;


                if (errorMessages) {
                    return errorMessages;
                } else if (message) {
                    return [message];
                }
            } catch (e) {
                return null;
            }
        },

        _showErrorMessage: function _showErrorMessage(html) {
            Messages.showErrorMsg(html, {
                closeable: true
            });
        },

        _showErrorDialogue: function _showErrorDialogue(message, heading) {
            return new ErrorDialog({
                heading: heading,
                message: message,
                mode: "warning"
            }).show();
        }
    });
});