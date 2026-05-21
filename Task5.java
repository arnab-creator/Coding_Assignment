//FIX:

public class DocumentValidator {
    // FIX: replaced printStackTrace with SLF4J logger for proper log control
    private static final Logger logger = LoggerFactory.getLogger(DocumentValidator.class);

    public ValidationResult validate(Document doc) {
        try {
            // FIX: replaced RuntimeException with warn log to stop flooding logs
            if (doc == null) {
                logger.warn("Validation failed: document is null");
                return ValidationResult.invalid("Document is null");
            }
            String content = doc.extractContent();
            if (content == null || content.isEmpty()) {
                logger.warn("Validation failed: empty document content");
                return ValidationResult.invalid("Empty content");
            }
            return runValidationRules(content);
        } catch (Exception e) {
            // FIX: unexpected errors logged at ERROR level with stack trace
            logger.error("Unexpected error during document validation", e);
            // FIX: was returning null, caused NullPointerException when caller did r.isValid()
            return ValidationResult.invalid("Validation processing error");
        }
    }

    public void validateBatch(List<Document> docs) {
        for (Document doc : docs) {
            try {
                ValidationResult r = validate(doc);
                // FIX: null check added, validate() now always returns non-null
                if (r != null && r.isValid()) {
                    saveResult(r);
                }
            } catch (Exception e) {
                // FIX: Silent catch replaced with ERROR logs so failures are visible.
                logger.error("Failed to process document in batch", e);
            }
        }
    }
}