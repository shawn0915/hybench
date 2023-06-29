drop SEQUENCE if exists transfer_id_seq cascade;
drop SEQUENCE if exists checking_id_seq cascade;
drop SEQUENCE if exists loanapps_id_seq cascade;
drop SEQUENCE if exists loantrans_id_seq cascade;

drop table if exists customer;
drop table if exists checkingaccount;
drop table if exists savingaccount;
drop table if exists transfer;
drop table if exists loanapps;
drop table if exists company;
drop table if exists loantrans;
drop table if exists checking;