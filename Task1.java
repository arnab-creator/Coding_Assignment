//FIX:

public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {
    // FIX: result was null, initialised to ArrayList to avoid NullPointerException on add()
    List<LoanAccount> result = new ArrayList<>();

    for (LoanAccount account : accounts) {
        // FIX: dueDate can be null for some accounts, added null check before calling .before()
        if (account.getDueDate() != null && account.getDueDate().before(new Date())) {
            // FIX: original > 0 is logically correct for excluding zero-balance
            //      accounts. No change to comparison value needed. But in a
            //      production banking system, double is unsafe for currency; BigDecimal
            //      should be used, but field type is outside this fix's scope hence just pointed out.
            if (account.getOutstandingBalance() > 0) {
                result.add(account);
            }
        }
    }
    return result;
}


