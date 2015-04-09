#This application is using number metawidget features and functionalities

# Metawidget Standard Features #

  * Annotations used : @UiLabel, @UiRequired, @UiComesAfter, @UiHidden, @UiMasked, @UiLarge

  * Metawidget Stubs Used
m:stub overriding feature is used to do the data table operations

  * PrimeFacesWidgetBuilder is used for the calendar gadget with @UiAttribute(name = "type", value = "java.util.Date")


---


# Custom Inspector #
A new custom MyJobsValidatorInspector extended from BaseObjectInspector is created to support some of the validations which are not available(AFAIK) from metawidget. The annotations are just direct casted in the class as I don't use any external annotations in the code. So chances of ClassCastException are not there.


---


# Custom Processor #
A new custom MyJobsValidationProcessor extended from WidgetProcessor is created to process the inspection results from MyJobsValidatorInspector.


---


# Equal Fields Validation #
Comparing the password and repeated password feature isn't there under the current metawidget model. So I introduced a new annotation called @EqualField where you specify the matching field. The inspect will set the matching field and then the processor will add the EqualityValidator with these fields. The game wasn't over by then as the the component id is required by the EqualityValidator, so instead of hardcoding, I added another Annotation called @CompId where you specify the component id, the Processor will set the new id there, which will be aptly used by the EqualityValidation. Some of the code from EqualityValidator is taken from SEAM project, the credits mentioned in the class as it is though the class is widely modified.


---


# Regex Validation #
Wanted to play arround with it, I could have simply used the existing annotation, but instead created my own @Regex annotation and a RegexValidator. Inspector and Processor do the rest of the job nicely!