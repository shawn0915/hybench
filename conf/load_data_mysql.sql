LOAD DATA LOCAL INFILE 'Data_1x/customer.csv' INTO TABLE customer FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE 'Data_1x/company.csv' INTO TABLE company FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE 'Data_1x/transfer.csv' INTO TABLE transfer FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE 'Data_1x/checking.csv' INTO TABLE checking FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE 'Data_1x/checkingAccount.csv' INTO TABLE checkingaccount FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE 'Data_1x/savingAccount.csv' INTO TABLE savingaccount FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE 'Data_1x/loanApps.csv' INTO TABLE loanapps FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INFILE 'Data_1x/loanTrans.csv' INTO TABLE loantrans FIELDS TERMINATED BY ',';