# Promethius

### A fast and easy way to build a real time web-socket dashboard

![](https://feminema.files.wordpress.com/2012/06/prometheus-film-head.jpg)

Promethius lets you quickly build reactive dashboards that update themselves in real time.

## How

Promethius can do two things.

1. Poll for data and update itself it real time (perfect for real time monitoring or dashboards)

2. Receive "push" messages and update itself in real time

## Deployment

This dashboard can be pushed to Heroku in under a minute.

1. Fork repo
2. git clone your-fork
3. heroku create
4. git push heroku master

## API

Promethius exposes a very simple API that lets you push data to the board. This might be the easiest option if you don't want to mess with Scala code too much.

## Create the markup

```html
<div class="col-md-6">
  @channel("counter", Some("A simple counter channel"))
</div>
```

## Push data to a matching channel

We perform a HTTP post and set the counter value to be equal to 1. The dashboard will update in real time to display the value

```
curl -iX POST http://localhost:9000/api/events -d '{"key": "counter","value": "1"}' -H "Content-Type: application/json"
```

## About

Promethius is an easy to use monitoring dashboard written in Scala. It uses web sockets to push real time updates directly
to the board. The idea is to give you "just enough" to build great looking real-time dashboards
while allowing you the freedom to customise things easily.

![](https://raw.githubusercontent.com/owainlewis/ripley/master/public/images/preview.png)

There are two ways to use the dashboard.

1. Use the API if you don't want to mess around with Scala code too much.
2. Create your own custom widgets using small amounts of Scala code

## Concept

Promethius is designed to be simple and easy to understand. 
There are only really three main concepts that you'll need to understand when creating channels.

1. Write a function that pushes data to a channel
2. Create a html item on the front end with the correct data attribute to display it
3. Optionally "poll" a service for new data using a Scheduler

### 1. Web Socket Stream

A global web socket stream is exposed in Scala code to which you can push data to. You push a "key" and a "value" to the stream. I.e

```scala

import backend.SocketStream

// Send a message to the channel. **Note that the value must be a string**

SocketStream.push("greeting", "OH HAI")

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

import backend.SocketStream
import scala.util.Random.nextInt

class SimpleWidget(key: String) extends Widget {
  def run() = {
    SocketStream.push(key, nextInt(1000).toString)
  }
}
```

Now the markup which lives in app/views/index.html

```html
@channel("random-number", Some("A random number channel"))
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



## Want to help?

Feel free to submit a pull request if you have ideas.
