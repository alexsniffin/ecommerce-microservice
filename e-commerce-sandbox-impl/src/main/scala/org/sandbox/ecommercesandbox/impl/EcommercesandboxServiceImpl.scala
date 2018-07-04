package org.sandbox.ecommercesandbox.impl

import org.sandbox.ecommercesandbox.api
import org.sandbox.ecommercesandbox.api.{EcommercesandboxService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}

/**
  * Implementation of the EcommercesandboxService.
  */
class EcommercesandboxServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends EcommercesandboxService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the e-commerce-sandbox entity for the given ID.
    val ref = persistentEntityRegistry.refFor[EcommercesandboxEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the e-commerce-sandbox entity for the given ID.
    val ref = persistentEntityRegistry.refFor[EcommercesandboxEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }


  override def greetingsTopic(): Topic[api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(EcommercesandboxEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[EcommercesandboxEvent]): api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}
