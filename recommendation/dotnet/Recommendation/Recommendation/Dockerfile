FROM mcr.microsoft.com/dotnet/core/aspnet:2.2-stretch-slim AS base
WORKDIR /app
EXPOSE 80

FROM mcr.microsoft.com/dotnet/core/sdk:2.2-stretch AS build
WORKDIR /src
COPY ["Recommendation/Recommendation.csproj", "Recommendation/"]
RUN dotnet restore "Recommendation/Recommendation.csproj"
COPY . .
WORKDIR "/src/Recommendation"
RUN dotnet build "Recommendation.csproj" -c Release -o /app

FROM build AS publish
RUN dotnet publish "Recommendation.csproj" -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
ENTRYPOINT ["dotnet", "Recommendation.dll"]