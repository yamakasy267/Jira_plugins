define('jira/marionette-3.1', [
    'atlassian/libs/underscore-1.8.3',
    'jira/backbone-1.3.3',
    'atlassian/libs/factories/backbone.radio-2.0.0',
    'atlassian/libs/factories/marionette-3.1.0'
], function (
    _,
    Backbone,
    BackboneRadioFactory,
    MarionetteFactory
) {
    const BackboneRadio = BackboneRadioFactory(_, Backbone);

    return MarionetteFactory(_, Backbone, BackboneRadio);
});