package org.sandbox.ecommercesandboxstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import org.sandbox.ecommercesandboxstream.api.EcommercesandboxStreamService
import org.sandbox.ecommercesandbox.api.EcommercesandboxService
import com.softwaremill.macwire._

class EcommercesandboxStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new EcommercesandboxStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new EcommercesandboxStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[EcommercesandboxStreamService])
}

abstract class EcommercesandboxStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[EcommercesandboxStreamService](wire[EcommercesandboxStreamServiceImpl])

  // Bind the EcommercesandboxService client
  lazy val ecommercesandboxService = serviceClient.implement[EcommercesandboxService]
}
