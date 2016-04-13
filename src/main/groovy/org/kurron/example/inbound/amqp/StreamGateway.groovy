/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurron.example.inbound.amqp

import org.kurron.example.MessagingContext
import org.kurron.example.core.EchoComponent
import org.kurron.feedback.AbstractFeedbackAware
import org.kurron.stereotype.InboundGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink

/**
 * Gateway that handles incoming messages from a message queue.
 */
@InboundGateway
class StreamGateway extends AbstractFeedbackAware {

    /**
     * Manages processing of the message.
     */
    private final EchoComponent theComponent

    @Autowired
    StreamGateway( final EchoComponent aComponent ) {
        theComponent = aComponent
    }

    @SuppressWarnings( 'GroovyUnusedDeclaration' )
    @StreamListener( Sink.INPUT )
    void processMessage( String message ) {
        feedbackProvider.sendFeedback( MessagingContext.JUST_HEARD, message )
        theComponent.processMessage( message )
    }
}
