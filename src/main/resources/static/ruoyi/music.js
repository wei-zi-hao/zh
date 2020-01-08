const player = new APlayer({
    element: document.getElementById('aplayer'),
    mini: false,
    autoplay: false,
    mutex: true,
    theme: '#00aae9',
    lrcType: false,
    order: 'list',
    audio: [{
        name: '处暑',
        artist: '音阙诗听,王梓钰',
        url: 'http://music.163.com/song/media/outer/url?id=1385858356.mp3',
        cover: 'http://p2.music.126.net/Zo2FKOT-7LemNjQnd4A6gQ==/109951164313505823.jpg?param=300x300'
    },{
        name: '惊蛰',
        artist: '音阙诗听,王梓钰',
        url: 'http://music.163.com/song/media/outer/url?id=862099159.mp3',
        cover: 'http://p2.music.126.net/5MmXpaP9r88tNzExPGMI8Q==/109951163370350985.jpg?param=300x300'
    },{
        name: 'Towers',
        artist: 'Frida Sundemo',
        url: 'http://music.163.com/song/media/outer/url?id=2639477.mp3',
        cover: 'http://p1.music.126.net/GITTTa1LD0qKX9eAW7epTg==/944480488315728.jpg?param=130y130'
    },{
        name: 'Pages of Love',
        artist: 'Ryan Farish',
        url: 'http://music.163.com/song/media/outer/url?id=25791608.mp3',
        cover: 'http://p2.music.126.net/ol5I7So8kHncjiFWLLYb8Q==/2262794929978909.jpg?param=130y130'
    },{
        name: 'Salt',
        artist: 'Ava Max',
        url: 'http://music.163.com/song/media/outer/url?id=1299557938.mp3',
        cover: 'http://p1.music.126.net/NYGM3ltk3HvEL_Vm94ZDCQ==/109951163451190172.jpg?param=130y130'

    },{
        name: 'Collide',
        artist: 'Rosi Golan / Vicetone',
        url: 'http://music.163.com/song/media/outer/url?id=493551046.mp3',
        cover: 'http://p1.music.126.net/HL9zam_SY3SZ2R3aSiBAxA==/18261788626286488.jpg?param=130y130'
    },{
        name: '六月的雨',
        artist: '胡歌',
        url: 'http://music.163.com/song/media/outer/url?id=92774.mp3',
        cover: 'http://p2.music.126.net/95TcQghGMiaAl5363ZTJbA==/109951163200250165.jpg?param=130y130'

    },{
        name: 'I Love You',
        artist: 'Axwell Λ Ingrosso / KiD Ink',
        url: 'http://music.163.com/song/media/outer/url?id=458725715.mp3',
        cover: 'http://p2.music.126.net/-uKBeYJbvezKse_Fl7160A==/18637821604210132.jpg?param=130y130'
    },{
        name: 'I Knew You Were Trouble',
        artist: 'Taylor Swift',
        url: 'http://music.163.com/song/media/outer/url?id=1333126628.mp3',
        cover: 'http://p2.music.126.net/QtHTu9JysVWTat30lFrXTA==/109951163208497947.jpg?param=130y130'
    },{
        name: 'Fly Away',
        artist: 'Anjulie / TheFatRat',
        url: 'http://music.163.com/song/media/outer/url?id=480648424.mp3',
        cover: 'http://p1.music.126.net/4N4a5Ct4BAxZvP4MAE0oXg==/17651559672748644.jpg?param=130y130'
    },{
        name: '再见再见',
        artist: '蒋劲夫 / 张云龙 / 杜天皓 / 魏大勋 / 李易峰',
        url: 'http://music.163.com/song/media/outer/url?id=32526583.mp3',
        cover: 'http://p1.music.126.net/C-mBD7WM1uKrkMx8Ag2Lpw==/7928578348097787.jpg?param=130y130'
    },{
        name: '浪漫空气',
        artist: '〆轉身回眸╮丶淚傾城ヅ',
        url: 'http://tx.stream.kg.qq.com/szkge/8ec7adf30d959fbe926f061b3b1aef06d3eca7b6?ftnrkey=378613071c14aaf33d4ef4fa18a7d3c4463732af4e142eb9c1b7494c162a3270c62994fbe5312139fd487d7094103d9ab794201176937aaa2075f908e1430adc&vkey=D57DDB1A4801476E6E24F2F7AB02E4AF9985E82C976CBEE40BB5AF8BD146D561D7E42146F53F122EDCB96E904AECDB13AFB82E326253378A216C527639D9667EA87A3DDF1908D179532E116E738DBC592639792FAA054870&fname=1021_e91bf6e85bb2d7ae547e47ef80f97afc698144ac.0.m4a&fromtag=1006&sdtfrom=v1006&ugcid=365997902_1566291646_381',
        cover: 'http://y.gtimg.cn/music/photo_new/T002R500x500M000000KUoSv1NpbQw.jpg?max_age=2592000',
    },{
        name: 'Explode',
        artist: 'Charli XCX',
        url: 'http://music.163.com/song/media/outer/url?id=412016841.mp3',
        cover: 'http://p2.music.126.net/EPYbXdDflk75KA7_pLie0A==/1390882217588656.jpg?param=130y130'
    },{
        name: 'Remnants',
        artist: 'NGC 3.14 / juunana',
        url: 'http://music.163.com/song/media/outer/url?id=423406940.mp3',
        cover: 'http://p2.music.126.net/H6wNDhvXpXKqINhE86bxJg==/3445869449303294.jpg?param=130y130'
    }, {
        name: '童话镇',
        artist: '陈一发',
        url: 'http://vip.baidu190.com/uploads/2017/20170958b6f5008ad538fde6286714a3457134.mp3',
        cover: 'http://ruoyi.vip/music/cover/chenyifa.jpg',
    }

    ]
});