package models

sealed trait WidgetType

case class SimpleWidget extends WidgetType

case class Widget(name: String, widgetType: WidgetType)
