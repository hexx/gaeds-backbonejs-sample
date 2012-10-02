$ ->
  class Message extends Backbone.Model

  class MessageList extends Backbone.Collection
    url: "/messages"

  Messages = new MessageList

  class MessageView extends Backbone.View
    tagName: "tr"

    template: _.template($('#message-template').html())

    render: ->
      @$el.html(@template(@model.toJSON()))
      this

  class BoardView extends Backbone.View
    el: $("#post-button")

    events:
      "click": "createMessage"

    initialize: ->
      Messages.on('add', @addMessage, this)
      Messages.on('reset', @addAllMessage, this)
      Messages.fetch()

    createMessage: ->
      Messages.create
        name: $("#name-input").val()
        message: $("#message-input").val()
        date: new Date().toISOString()

    addMessage: (message) ->
      view = new MessageView({model: message})
      $("#message-list").prepend(view.render().el)

    addAllMessage: ->
      Messages.each(@addMessage)

  Board = new BoardView
