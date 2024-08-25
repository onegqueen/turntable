import urllib

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List
from selenium import webdriver as wb
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager

app = FastAPI()

class Song(BaseModel):
    songId: int
    name: str
    artists: List[str]
    albumName: str

class SongRequest(BaseModel):
    songs: List[Song]

options = wb.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
options.add_argument(f'user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36')

service = Service(executable_path=ChromeDriverManager().install())


#json request 요청양식은 이쪽에서 정의
@app.post("/getYoutubeUrls")
async def get_youtube_urls(request: SongRequest):
    driver = wb.Chrome(service=service, options=options)
    results = []
    for song in request.songs:
        song_id = song.songId
        name = song.name
        artists = song.artists
        album_name = song.albumName
        video_url = None

        # YouTube 검색 URL 생성
        #search_query = f"{name}+{'+'.join(artists)}+{'official'}+{'MV'}"
        search_query = f"{name} {' '.join(artists)} official MV"
        encoded_search_query = urllib.parse.quote(search_query)
        youtube_search_url = (
            f"https://www.youtube.com/results?search_query={encoded_search_query}"
        )
        driver.get(youtube_search_url)
        wait = WebDriverWait(driver, 3)
        try:
            video_element = wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, "ytd-video-renderer a#thumbnail")))
            video_url = video_element.get_attribute("href")
        except TimeoutException:
            video_url = None

        # 결과에 추가
        results.append({
            "songId": song_id,
            "name": name,
            "artists": artists,
            "albumName": album_name,
            "youtubeUrl": video_url if video_url else "URL not found"
        })
    return {"results": results}

