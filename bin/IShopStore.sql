use IShopStore
go

select* from dbo.roles
delete from dbo.roles

ALTER TABLE roles ALTER COLUMN name NVARCHAR(50);
ALTER TABLE roles ALTER COLUMN description NVARCHAR(255);


insert into dbo.roles(name, description)
values (N'ADMIN',N'Admin thì full quyền'),
(N'USER',N'User thông thường');