package org.demo.buildyourown.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.binding.message.Message;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

public final class ValidationUtils
{



    public static final String MISSING_ERROR = "--missing error msg--";

    public static void addDefaultTextError(final MessageContext context, final String defaultText)
    {
        context.addMessage(new MessageBuilder().error().defaultText(defaultText).build());
    }

    public static void addDefaultTextError(final ValidationContext context, final String defaultText)
    {
        MessageContext messages = context.getMessageContext();
        addDefaultTextError(messages, defaultText);

    }

    public static void addError(final MessageContext context, final String code)
    {
        context.addMessage(new MessageBuilder().error().defaultText(MISSING_ERROR).code(code).build());
    }
    
    public static void addErrorMessage(final ValidationContext context, String field, String message){
    	MessageContext messageContext = context.getMessageContext();
    	addErrorMessage(messageContext, field, message);
    }
	public static final void addErrorMessage(MessageContext context, String field,
			String message) {

		context.addMessage(new MessageBuilder().error().defaultText(message).source(field).build());
	}
    
    public static void addError(final MessageContext messages, final String field, final String code)
    {
        messages.addMessage(new MessageBuilder().error().defaultText(MISSING_ERROR).source(field).code(code).build());
    }

    public static void addError(final ValidationContext context, final String code)
    {
        MessageContext messages = context.getMessageContext();
        addError(messages, code);
    }

    public static void addError(final ValidationContext context, final String field, final String code)
    {
        MessageContext messages = context.getMessageContext();
        addError(messages, field, code);
    }

    public static void addErrorWithArgs(final MessageContext context, final String code, final Object... args)
    {
        context.addMessage(new MessageBuilder().error().defaultText(MISSING_ERROR).code(code).args(args).build());
    }

    public static void addErrorWithArgs(final ValidationContext context, final String code, final Object... args)
    {
        MessageContext messages = context.getMessageContext();
        addErrorWithArgs(messages, code, args);
    }

    public static List<String> findErrorMessages(final ValidationContext context)
    {
        List<String> errors = new ArrayList<String>();
        for (Message message : context.getMessageContext().getAllMessages())
        {
            errors.add(message.getText());
        }
        return errors;
    }
 
    private ValidationUtils()
    {

    }
}