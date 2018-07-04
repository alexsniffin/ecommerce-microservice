package org.sandbox.ecommercesandboxstream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The e-commerce-sandbox stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and
  * consume the EcommercesandboxStream service.
  */
trait EcommercesandboxStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor = {
    import Service._

    named("e-commerce-sandbox-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
  }
}

