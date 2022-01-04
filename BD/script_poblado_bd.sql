select * from producto p ;
select * from categoria c ;
select * from cat_tienda ct ;
select * from tienda t ;
select * from compra c ;
select * from gerente g ;

INSERT INTO categoria (category_name,category_description) VALUES
	 ('Refrescos','Contiene marcas de refrescos bastantes conocidas'),
	 ('Galletas','Contiene marcas de galletas'),
	 ('Papas','Contiene marcas de papas'),
	 ('Paletas','Contiene marcas de paletas'),
	 ('Harinas','Contiene marcas de harinas'),
	 ('Papel','Contiene marcas de papel higienico'),
	 ('Jabon','Contiene marcas de jabon'),
	 ('Pan','Contiene marcas de pan'),
	 ('Carne','Contiene marcas de carnes frias'),
	 ('Semillas','Contiene diferentes semillas'),
	 ('Lacteos','Contiene lacteos'),
	 ('Cerveza','Contiene marcas de cerveza'),
	 ('Cigarros','Contiene marcas de cigarros'),
	 ('Enlatados','Contiene enlatados'),
	 ('Mayonesa','Contiene marcas de mayonesas'),
	 ('Croquetas','Contiene marcas de croquetas para mascotas'),
	 ('Jugos','Contiene marcas de jugos de frutas'),
	 ('Bebidas Energéticas','Contiene marcas de bebidas energetizantes'),
	 ('Vinos','Contiene diferentes marcas de bebidas alcoholicas'),
	 ('Productos de limpieza','Contiene diferentes productos para limpieza del hogar'),
	 ('Paletas de hielo','Contiene marcas de paletas de hielo de sabores'),
	 ('Helados','Contiene marcas de helados y sandwiches helados'),
	 ('Televisores','Contiene marcas de televisores de plasma y LCD'),
	 ('Llantas','Contiene marcas de llantas para automoviles'),
	 ('Aceite comestible','Contiene marcas de aceites comestibles'),
	 ('Aceite para auto','Contiene marcas de aceites lubricantes para automoviles'),
	 ('Computadoras','Contiene marcas de computadoras portatiles y de escritorio'),
	 ('Telefonos Celulares','Contiene marcas de telefonos celulares Android y IOS'),
	 ('Tablets','Contiene marcas de tabletas electronicas Android y IOS'),
	 ('Bocinas','Contiene marcas de bocinas portatiles'),
	 ('Categoría Genéricaás','Para todos aquellos productos genéricos'),
	 ('Videojuegos','Contiene marcas de consolas de videojuegos');
	 
	
	INSERT INTO producto (product_name,product_description,product_price,product_stock,category_id) VALUES
	 ('Coca-Cola','Bebida carbonatada sabor cola',1.0,500,10),
	 ('Galletas Chokis','Galletas con chispas de chocolate',13.0,501,2),
	 ('Ruffles Queso','Frituras a base de papa sabor queso',15.0,600,3),
	 ('Paleta Tutsi Pop','Paleta hecha de caramelo macizo sabor cereza',5.0,400,4),
	 ('Harina Tres Estrellas','Harina para hot cackes',35.0,250,5),
	 ('Papel Petalo','Papel higienico doble hoja',30.0,200,6),
	 ('Jabon Axion','Jabon liquido para trastes',32.0,100,7),
	 ('Pan Blanco Bimbo','Pan blanco de caja',45.0,500,8),
	 ('Jamon Fud','Jamon de pierna',30.0,500,9),
	 ('Chia','Semilla por kilo',50.0,80,10),
	 ('Leche Lala','Leche pasteurizada',22.0,300,11),
	 ('Cerveza Modelo','Cerveza Oscura',35.0,600,12),
	 ('Cigarros Marlboro','Cajetiila con 20 cigarros',60.0,1000,13),
	 ('Chiles La costeña','Chiles enteros en escabeche',15.0,200,14),
	 ('Mayonesa McKormick','Mayonesa',45.0,100,15),
	 ('Pedigree Adulto','Croquetas para perros de 1+ años por kilo',38.0,150,16),
	 ('Jumex Durazno','Bebida con nectar sabor durazno',18.0,200,17),
	 ('Monster','Bebida energetica',30.0,180,18),
	 ('Johnny Walcker','Bebida alcoholica Whiskey',380.0,100,19),
	 ('Desinfectante Lysol','Desinfectante en spray',58.0,100,20),
	 ('Solero Limon','Paleta helada sabor limon',25.0,100,21),
	 ('Helado Cornetto','Cono de galleta con helado sabor vainilla',32.0,100,22),
	 ('Samsung Smart 32','Television inteligente de 32 pulgadas',4999.0,100,23),
	 ('Pirelli Cinturato','Llanta para auto, linea cinturato',1799.0,50,24),
	 ('Aceite Nutrioli','Aceite vegetal',38.0,200,25),
	 ('Aceite Quaker State','Aceite sintetico para motores de combustion interna',1150.0,50,26),
	 ('Dell Inspiron 6420','Laptop Empresarial Intel CoreI5',16699.0,10,27),
	 ('Xiaomi Redmi Note 11','Telefono inteligente, sistema Android 12',11999.0,20,28),
	 ('Ipad','Tableta inteligente con IOS',18399.0,15,29),
	 ('Super nuevo producto','Este es un producto super nuevo',103.0,2,12);
	 