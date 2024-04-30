DO 
$$
BEGIN 
    CREATE TABLE IF NOT EXISTS Account(
        Id UUID PRIMARY KEY DEFAULT gen_random_uuid() not null,
        Username varchar(100) UNIQUE not null,
        Password TEXT
    );
    CREATE TABLE IF NOT EXISTS Asset(
        Id UUID PRIMARY KEY DEFAULT gen_random_uuid() not null,
        Name varchar(100) UNIQUE not null,
        PicturePath varchar(200) not null
    );
    CREATE TABLE IF NOT EXISTS Item(
        Id UUID PRIMARY KEY DEFAULT gen_random_uuid() not null,
        AssetId UUID REFERENCES Asset(Id) not null,
        AccountId UUID REFERENCES Account(Id) not null,
        CreatedOn date not null
    );
    CREATE TABLE IF NOT EXISTS Recipe(
        Id UUID PRIMARY KEY DEFAULT gen_random_uuid() not null,
        AssetOutId UUID REFERENCES Asset(Id) not null,
        Quantity int not null
    );
    CREATE TABLE IF NOT EXISTS AssetIn(
        Id UUID PRIMARY KEY DEFAULT gen_random_uuid() not null,
        AssetId UUID REFERENCES Asset(Id) not null,
        RecipeId UUID REFERENCES Recipe(Id) not null,
        Quantity int not null,
        Col int not null,
        Row int not null
    );
END 
$$;
