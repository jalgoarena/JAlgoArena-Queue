module.exports = {
    apps: [
        {
            name: 'queue',
            args: [
                "-jar",
                "build/libs/jalgoarena-queue-2.1.0-SNAPSHOT.jar"
            ],
            script: 'java',
            env: {
                PORT: 5007,
                BOOTSTRAP_SERVERS: 'localhost:9092,localhost:9093,localhost:9094'
            }
        }
    ]
};
