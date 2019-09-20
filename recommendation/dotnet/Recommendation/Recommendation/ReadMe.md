### Commandline Build

```
dotnet.exe publish -c Release -r win10-x64 --self-contained=true 
```

### Build Docker Image (from solution folder)

As part of this Docker image build, the project will also be built in a build container.

```
docker build -f ".\Recommendation\Dockerfile" -t recommendation:prod --target final . 
```