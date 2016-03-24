BEGIN TRANSACTION;
DROP TABLE IF EXISTS "product_master";
CREATE TABLE "product_master" ("_id" INTEGER PRIMARY KEY  NOT NULL ,"product_name" TEXT,"product_desc" TEXT,"product_image" TEXT,"product_price" TEXT);
INSERT INTO "product_master" VALUES(1,'Moto G (3rd Generation) (Black, 16GB)','Key Features of Moto G (3rd Generation)

-IPX7 Water Resistance
-13 MP Primary Camera
-5 MP Secondary Camera
-2470 mAh Long Lasting Battery
-Qualcomm Snapdragon 410 (MSM8916) Processor with 1.4 GHz Quad Core CPU, Adreno 306 with 400 MHz GPU
-4G LTE
-5 inch, 720p HD (1280 x 720), Corning Gorilla Glass 3
-Android v5.1.1 (Lollipop) OS
-Dual Sim (GSM + LTE)
-Expandable Storage Capacity of 32 GB',NULL,'12999');
INSERT INTO "product_master" VALUES(2,'Moto G (3rd Generation) (Black, 8GB)','Key Features of Moto G (3rd Generation)

-IPX7 Water Resistance
-13 MP Primary Camera
-5 MP Secondary Camera
-2470 mAh Long Lasting Battery
-Qualcomm Snapdragon 410 (MSM8916) Processor with 1.4 GHz Quad Core CPU, Adreno 306 with 400 MHz GPU
-4G LTE
-5 inch, 720p HD (1280 x 720), Corning Gorilla Glass 3
-Android v5.1.1 (Lollipop) OS
-Dual Sim (GSM + LTE)
-Expandable Storage Capacity of 32 GB',NULL,'11999');
INSERT INTO "product_master" VALUES(3,'Life of Mahatma Gandhi, The (English) (Paperback)','Author: fischer, louis|author
Released: 2006
Publisher: Harper Collins Publishers India',NULL,'599');
INSERT INTO "product_master" VALUES(4,'The Impatient Optimist : Bill Gates In His Own Words (English) (Paperback)','Author: Edited by Rogak, Lisa
Released: 2012
Publisher: Collins Business',NULL,'199');
INSERT INTO "product_master" VALUES(5,'Jesus Freak','Author: D C Talk
Published: 1969',NULL,'1245');
INSERT INTO "product_master" VALUES(6,'My Journey : Transforming Dreams into Actions (English) (Paperback)','Author: Kalam A P J
Released: 2013
Publisher: Rupa Publications',NULL,'135');
INSERT INTO "product_master" VALUES(7,'Samsung Galaxy J5 (8GB)','Key Features of Samsung Galaxy J5

-Android v5.1 (Lollipop) OS
-13 MP Primary Camera
-5 MP Secondary Camera with Front Flash
-5 inch Super AMOLED Capacitive Touchscreen
-1.2 GHz Qualcomm MSM8916 Quad Core Processor
-Dual Standby Sim (LTE + GSM)
-Expandable Storage Capacity of 128 GB
-4G (LTE) - Cat 4',NULL,'11999');
INSERT INTO "product_master" VALUES(8,'Do Bhai Cutwork Bellies','Ideal For: Women
Occasion:	Casual, Party
Material: Synthetic leather',NULL,'470');
INSERT INTO "product_master" VALUES(9,'Do Bhai M Bellies','Ideal For: Women
Occasion: Casual, Party',NULL,'699');
INSERT INTO "product_master" VALUES(10,'Sam Stefy Leather Heels','Ideal For:	Women
Occasion:	Party
Heel height: 4 inch',NULL,'799');
INSERT INTO "product_master" VALUES(11,'Nell Boots','Ideal for:  Women

SHOE DETAILS
Closure:Zipper, Buckle
Boot Shaft Height: 6
Tip Shape: Round
Weight: 290 gm (per single Shoe) - Weight of the product may vary depending on size.
Heel Height:  2.5
Style: Raised Ankle, Panel and Stitch Detail, Strap Detail, Textured Detail',NULL,'1785');
COMMIT;
