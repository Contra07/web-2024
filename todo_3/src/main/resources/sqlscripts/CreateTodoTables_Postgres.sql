DO 
$$
BEGIN 
    CREATE TABLE IF NOT EXISTS Project
    (
        Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        Name varchar(100) UNIQUE,
        Description TEXT,
        StartDate DATE,
        EndDate DATE
    );

    CREATE TABLE IF NOT EXISTS Task
    (
        Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        ProjectId UUID REFERENCES Project(Id),
        Name varchar(100) UNIQUE,
        Description TEXT,
        EndDate date,
        Done boolean
    );
END 
$$;