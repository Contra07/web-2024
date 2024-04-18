DO 
$$
BEGIN 
    CREATE TABLE IF NOT EXISTS Project
    (
        Id SERIAL PRIMARY KEY,
        Name varchar(100),
        Description TEXT,
        StartDate DATE,
        EndDate DATE
    );

    CREATE TABLE IF NOT EXISTS Task
    (
        Id SERIAL PRIMARY KEY,
        ProjectId SERIAL REFERENCES Project(Id),
        Name varchar(100),
        Description TEXT,
        EndDate date,
        Done boolean
    );
END 
$$;