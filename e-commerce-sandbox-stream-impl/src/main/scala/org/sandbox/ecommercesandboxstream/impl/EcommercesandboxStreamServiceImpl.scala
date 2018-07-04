package org.sandbox.ecommercesandboxstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.sandbox.ecommercesandboxstream.api.EcommercesandboxStreamService
import org.sandbox.ecommercesandbox.api.EcommercesandboxService

import scala.concurrent.Future

/**
  * Implementation of the EcommercesandboxStreamService.
  */
class EcommercesandboxStreamServiceImpl(ecommercesandboxService: EcommercesandboxService) extends EcommercesandboxStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(ecommercesandboxService.hello(_).invoke()))
  }
}
