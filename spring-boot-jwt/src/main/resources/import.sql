/* */
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Félix', 'Serna Olguín', 'fantonioserna18@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('John', 'Doe', 'john.doe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Jane', 'Doe', 'jane.doe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Erich', 'Gamma', 'erich.gamma@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Richard', 'Helm', 'richard.helm@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('John', 'Vlissides', 'john.vlissides@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('James', 'Gosling', 'james.gosling@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Bruce', 'Lee', 'bruce.lee@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Johnny', 'Doe', 'johnny.doe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('John', 'Roe', 'john.roe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Jane', 'Roe', 'jane.roe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Richard', 'Doe', 'richard.doe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Janie', 'Doe', 'janie.doe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Phillip', 'Webb', 'phillip.webb@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Sam', 'Brannen', 'sam.brannen@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Janie', 'Roe', 'janie.roe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('John', 'Smith', 'john.smith@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('John', 'Stiles', 'john.stiles@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Richard John', 'Roe', 'richard.doe@gmail.com', '2019-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Richard', 'Doe', 'richard.roe@gmail.com', '2019-08-07', '');

/* */
INSERT INTO productos (nombre, precio, create_at) VALUES ('Panasonic Pantalla LCD', 10000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Camara Digital DSC-W320B', 4200, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Apple iPod Shuffle', 3000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Notebook Z110', 14000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Hewlett Packard Multifuncional F2280', 3100, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Bianchi Bicicleta Aro 26', 2000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Mica Comoda 5 Cajones', 7500, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('HP Pavilion 15.6 Pulgadas', 13000, NOW());

/* */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura Bicicleta', '¡Alguna nota imporatante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (3, 2, 6);

/* Creamos algunos usuarios con sus roles */
INSERT INTO users (username, password, enabled) VALUES ('felix', '$2a$10$l8Jc3z.HXUM3vlD5feiV.OsJPmJ7my9qqVDR2QSs/Hg8a/eFy8ef.', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$ACgK58KACyLoItxnWft62eBopkhwwtBwVPUOOF9mD2Zl9Pp7BT95K', 1);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');
