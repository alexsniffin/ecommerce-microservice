package org.sandbox.ecommercesandbox.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import org.sandbox.ecommercesandbox.api.EcommercesandboxService
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.softwaremill.macwire._

class EcommercesandboxLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new EcommercesandboxApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new EcommercesandboxApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[EcommercesandboxService])
}

abstract class EcommercesandboxApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[EcommercesandboxService](wire[EcommercesandboxServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = EcommercesandboxSerializerRegistry

  // Register the e-commerce-sandbox persistent entity
  persistentEntityRegistry.register(wire[EcommercesandboxEntity])
}
