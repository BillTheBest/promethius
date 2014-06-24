# Promethius

### A fast and easy way to build a real time websocket dashboard

Promethius is an easy to use monitoring dashboard written in Scala. It uses web sockets to push real time updates directly
to the board. The idea is to give you "just enough" to build great looking real-time dashboards
while allowing you the freedom to customise things easily.

![](https://raw.githubusercontent.com/owainlewis/ripley/master/public/images/preview.png)

There are two ways to use the dashboard.

1. Use the API if you don't want to mess around with Scala code too much.
2. Create your own custom widgets using small amounts of Scala code

## Concept

Promethius is designed to be simple and easy to understand. There are only really two main concepts that you'll need to understand when creating widgets.

### 1. Websocket Channel

A global web-socket channel is exposed in Scala code to which you can push data to. You push a "key" and a "value" to the channel. I.e

```scala

import backend.SocketChannel

// Send a message to the channel. **Note that the value must be a string**

SocketChannel.push("greeting", "OH HAI")

```

You can think of all events in the system as messages with a key and a value.

When an event is pushed to the channel, the JavaScript front end looks for the key and a HTML entity with a data-key value set.
If it finds one the front end HTML gets updated in real time.

```html
<div data-key="greeting" class="widget red">
  <div class="value"></div> <!-- this will update to OH HAI when hooked up -->
</div>
```

This is the basis of the entire system. You push messages to a web-socket channel and if a corresponding HTML widget exists it will update in real time.

### 2. Scheduler

A scheduler is provided for running tasks at specific intervals.

The scheduler takes a single function with no parameters and a polling interval in seconds.


```scala

import actors.Scheduler

object Global extends GlobalSettings {
  def startApplication() {
    val a = new TimeWidget("time")
    Scheduler.start(a.run, 10) // call the run function every 10 seconds
  }
}

```

## A full example

Let's take a look at the simplest possible widget which pushes a Random integer to the front end every 10 seconds. We will start with the Scala code.

Some example widgets are available in app/widgets to help you get started

```scala
package widgets

import backend.SocketChannel
import scala.util.Random.nextInt

class SimpleWidget(key: String) extends Widget {
  def run() = {
    SocketChannel.push(key, nextInt(1000).toString)
  }
}
```

Now the markup which lives in app/views/index.html

```html
<div data-key="random-number" class="widget blue">
  <div class="value"></div>
</div>
```

And finally let's hook up the widget to a scheduler and run it every 5 seconds. This is done in app/Global.scala

```scala

import actors.Scheduler

object MyApp {

  def start() = {
    val b = new SimpleWidget("random-number")
    Scheduler.start(b.run, 5)
  }
}
```

## API

Promethius exposes a very simple API that lets you push data to the board. This might be the easiest option if you don't want to mess with Scala code too much.

Let's start with a simple example. We have a web application that we want to monitor for 500 errors. Every time a 500 error happens in our web app we want to update
our Promethius dashboard to display the error.

To do this we only need to do two things.

1. Create the widget to be updated on the front end.
2. Make a HTTP post request with JSON data to /api/metrics

## Creating the widget

Let's create the widget. We need to add the widget HTML to the front end.

In app/views/index.scala.html add the following code

```html
<div class="col-md-4">
  <div data-key="post-widget" class="widget green">
  <div class="value">AWAITING...</div>
  </div>
</div>
```

We now need to create a new PostWidget instance in app/Global.scala and give it the correct key

Your code should look something like this.

```scala
object Global extends GlobalSettings {

  def runWidgets() {

    // This is where we create our widget with a key of "post-metric"
    new PostWidget("post-metric")
  }

  // ...
}
```

## Updating the widget

Right now the widget should be idle. To update the widget make a HTTP post request to /api/metrics.

Here is an example of how you might set this up in a Ruby on Rails application that calls log_error every time a 500 error occurs.

```ruby

# When a 500 error happens in our Rails application, 
# push the error message onto Promethius
def log_error(error_message)
  # What we will post to the Promethius API
  message = { 'key' => 'post-metric', 'value' => error_message }.to_json
  RestClient.post "http://reaktor.mycompany.com/api/metrics", 
    message, :content_type => :json
end
```

## Want to help?

Feel free to submit a pull request if you have ideas.
