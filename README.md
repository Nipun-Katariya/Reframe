# Reframe

A hybrid Java-Python video upscaler that demonstrates cross-language process communication.

## Project Structure

```
Reframe/
├── engine/
│   └── reframe_core.py      # Python video processing engine
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── reframe/
│                   └── Bridge.java  # Java bridge for Python execution
└── pom.xml                  # Maven configuration
```

## Features

- **Python Engine**: Simulates video processing with progress updates
- **Java Bridge**: Executes Python script and captures real-time progress via ProcessBuilder
- **Real-time Progress**: Thread-based stdout reading for immediate progress capture
- **Maven Build**: Standard Maven project structure

## Prerequisites

- Java 11 or higher
- Python 3
- Maven 3.6+

## Building

```bash
mvn compile
```

## Running

```bash
# Run the Java Bridge
java -cp target/classes com.reframe.Bridge <video_path>

# Or directly run the Python engine
python3 engine/reframe_core.py <video_path>
```

## Example

```bash
java -cp target/classes com.reframe.Bridge sample_video.mp4
```

Output:
```
Processing video: sample_video.mp4
Captured: PROGRESS:20
Captured: PROGRESS:40
Captured: PROGRESS:60
Captured: PROGRESS:80
Captured: PROGRESS:100
Processing complete!
Process finished with exit code: 0
```