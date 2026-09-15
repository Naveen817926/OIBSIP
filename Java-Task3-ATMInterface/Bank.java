import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {

        accounts = new ArrayList<>();

        accounts.add(new Account("A1001", "user1", "1234", 10000));
        accounts.add(new Account("A1002", "user2", "5678", 5000));
    }

    public Account authenticate(String userId, String pin) {

        for (Account account : accounts) {

            if (account.getUserId().equals(userId)
                    && account.getPin().equals(pin)) {

                return account;
            }
        }

        return null;
    }

    public Account findAccount(String accountId) {

        for (Account account : accounts) {

            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }

        return null;
    }

    public boolean transfer(String fromAccountId,
                            String toAccountId,
                            double amount) {

        Account sender = findAccount(fromAccountId);
        Account receiver = findAccount(toAccountId);

        if (sender == null || receiver == null) {
            return false;
        }

        if (amount <= 0 || amount > sender.getBalance()) {
            return false;
        }

        sender.withdraw(amount);
        receiver.deposit(amount);

        return true;
    }
}