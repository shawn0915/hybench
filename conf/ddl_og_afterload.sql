alter table customer add constraint customer_pkey primary key (custID);
alter table company add constraint company_pkey primary key (companyID);
alter table savingAccount add constraint savingAccount_pkey primary key (accountID);
alter table checkingAccount add constraint checkingAccount_pkey primary key (accountID);
alter table transfer add constraint transfer_pkey primary key (id);
alter table checking add constraint checking_pkey primary key (id);
alter table loanapps add constraint loanapps_pkey primary key (id);
alter table loantrans add constraint loantrans_pkey primary key (id);

CREATE SEQUENCE transfer_id_seq;
SELECT SETVAL('transfer_id_seq', (SELECT max(id) FROM transfer));
ALTER TABLE transfer ALTER COLUMN id SET DEFAULT nextval('transfer_id_seq'::regclass);
ALTER SEQUENCE transfer_id_seq OWNED BY transfer.id;

CREATE SEQUENCE checking_id_seq;
SELECT SETVAL('checking_id_seq', (SELECT max(id) FROM checking));
ALTER TABLE checking ALTER COLUMN id SET DEFAULT nextval('checking_id_seq'::regclass);
ALTER SEQUENCE checking_id_seq OWNED BY checking.id;

CREATE SEQUENCE loanapps_id_seq;
SELECT SETVAL('loanapps_id_seq', (SELECT max(id) FROM loanapps));
ALTER TABLE loanapps ALTER COLUMN id SET DEFAULT nextval('loanapps_id_seq'::regclass);
ALTER SEQUENCE loanapps_id_seq OWNED BY loanapps.id;

CREATE SEQUENCE loantrans_id_seq;
SELECT SETVAL('loantrans_id_seq', (SELECT max(id) FROM loantrans));
ALTER TABLE loantrans ALTER COLUMN id SET DEFAULT nextval('loantrans_id_seq'::regclass);
ALTER SEQUENCE loantrans_id_seq OWNED BY loantrans.id;