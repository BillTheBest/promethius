# Ripley

Ripley is an easy to use monitoring dashboard written in Scala. It uses websockets to push real time updates directly to the board.

![](https://raw.githubusercontent.com/owainlewis/ripley/master/public/images/preview.png)

## Concept

Ripley is designed to be simple and easy to userstand. There are two main concepts that you'll need to understand when creating widgets.

1. A global websocket channel is exposed in Scala code to which you can push data to

```scala
import backend.SocketChannel

// Send a message to the channel

SocketChannel.push("OH HAI")

```

You will need to write your own JavaScript and CSS to display data from the websocket on the front end

2. A scheduler is provided for running tasks at specific intervals

The scheduler takes a single function with no parameters that is called. It is expected that this function will push something the channel

```scala

class MyWidget extends Widget {

  def getUrl(url: String) = {
    WS.url(url).get().map { response =>
      // Push the data onto the socket channel
      SocketChannel.push(response.body)
    }
  }

  def run()= {
    getUrl("http://time.jsontest.com")
  }
}

```

## Simple widgets

Here is a simple widget that polls JSON from a web endpoint

```scala
 val w = new PollingWidget("http://time.jsontest.com")
 Scheduler.start(w.run)
```


