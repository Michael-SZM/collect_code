# collect_code

![image](https://github.com/Michael-SZM/collect_code/blob/master/yl.png)

添加富文本工具类，支持快速设置各种文本样式，包括点击事件和图片添加，可以灵活组合使用。使用见Demo

```
findViewById<TextView>(R.id.latex01).apply {
            movementMethod = LinkMovementMethod.getInstance()
            val msg = SpannableHelper("默认值")
                .opString("第一段文本，世界美好", color = Color.RED).opString("\n")
                .opString(
                    "第二段文本，岁月静好",
                    color = Color.BLUE,
                    absoluteSizeConfig = SpannableHelper.AbsoluteSizeConfig( 25, true)
                )
                .opString(
                    "第三段文本，风和人和",
                    color = Color.RED,
                    backgroundColorConfig = SpannableHelper.BackgroundColorConfig(
                        Color.YELLOW
                    )
                )
                .opString(
                    "第四段文本，世界美好",
                    color = Color.BLACK,
                    clickConfig = SpannableHelper.ClickConfig( true, action = {
                        Toast.makeText(this@LatexActivity, "拒绝不美好的对象", Toast.LENGTH_SHORT).show()
                    })
                )
                .opString(
                    "第五段文本，世界美好,隐藏下划线",
                    clickConfig = SpannableHelper.ClickConfig( false, action = {
                        Toast.makeText(this@LatexActivity, "拒绝不美好的对象", Toast.LENGTH_SHORT).show()
                    }),
                    rSize = 1.1f
                )
                .opString(
                    "第六段文本，模糊 模糊 模糊", color = Color.GREEN, maskFilter = BlurMaskFilter(
                        1.5f,
                        BlurMaskFilter.Blur.SOLID
                    ), style = Typeface.BOLD, rSize = 1.1f
                )
//                .opString("  第七段文本，世界美好",color = Color.RED,maskFilter = EmbossMaskFilter(floatArrayOf(1f,1f,1f),0.4f, 6f, 3.5f))
//                .opString("\n")
                .opString("上标", superscript = true, color = Color.GRAY, rSize = 0.5f)
                .opString("演示脚注").opString("脚注", subscript = true, rSize = 0.5f)
                .opString("删除线", strikethrough = true)
                .opString("字体", typefaceConfig = SpannableHelper.TypefaceConfig( "monospace"))
                .appendImage(resources.getDrawable(R.drawable.icon_bulb), 40, 40)
                .opString(
                    "我是url演示",
                    urlConfig = SpannableHelper.UrlConfig("http:www.baidu.com", Color.RED, false),
                    rSize = 1.2f,
                    style = SpannableHelper.TypefaceStyleDef.BOLD
                )
                .build()
            text = msg
        }
        
        
        // 加载网络图片，配合glide
        
        
        findViewById<TextView>(R.id.latex08).apply {
            val msg = SpannableHelper("mmmm")
//                .opString("xxxxxxxxxxxx KLLLNKSLKKKKKKKKKKKKKKKKKKSSSSSSSSS ksjnlk是内裤男老师那里的可能拉深南电路哪里是你的拉链深南电路")
                .opString("xxxxxxxxxxxx  ksjnlk是内裤男老师那里的可能拉深南电路哪里是你的拉链深南电路")
                .appendImage(
                    "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3350497021,3015906272&fm=26&gp=0.jpg",
                    60F,
                    60F,
                    this,
                    loadAction = { url, tv, defaultImageSpan ->
                        Glide.with(tv)
                            .asBitmap()
                            .load(url)
                            .into(SpannableHelper.CusTarget(tv, defaultImageSpan))
                    }
                )
                .opString("xxxxxxxxxxxx")
                .build()
            text = msg
        }
        
        // 加载lable标签，支持自定义背景色，背景圆角，左右外边距等多种属性
        
        findViewById<TextView>(R.id.latex09).apply {
            movementMethod = LinkMovementMethod.getInstance()
            val msg = SpannableHelper("mmmm")
                .addLabelText(
                    this@LatexActivity,
                    "标签一",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .addLabelText(
                    this@LatexActivity,
                    "标",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .addLabelText(
                    this@LatexActivity,
                    "签",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .opString("skjbkjkjnknjk 看接口尽可能看接口看见你看见你空间即可可能就看你能看见看见看见看见你就能看见你尽快将可能就可能就能看空间")
                .addLabelText(
                    this@LatexActivity,
                    "标",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .appendImage(
                    "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3350497021,3015906272&fm=26&gp=0.jpg",
                    60F,
                    60F,
                    this,
                    loadAction = { url, tv, defaultImageSpan ->
                        Glide.with(tv)
                            .asBitmap()
                            .load(url)
                            .into(SpannableHelper.CusTarget(tv, defaultImageSpan))
                    }
                )
//                .opString("xxxxxxxxxxxx")
                .addLabelText(
                    this@LatexActivity,
                    "元",
                    bgColor = Color.parseColor("#8eff8956"),
                    aTextSize = 40f,
                    cornerRadius = 4f,
                    leftMargin = 0f,
                    rightMargin = 0f,
                    action = {
                        Toast.makeText(this@LatexActivity,"标签被点击了",Toast.LENGTH_SHORT).show()
                    }
                )
                .build()
            text = msg
        }
        
        // 加载本地图
        
        findViewById<TextView>(R.id.latex07).apply {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_bulb)
            val msg = SpannableHelper("mmmm")
                .appendImage(this@LatexActivity, bitmap)
                .build()
            text = msg
        }
        
        findViewById<TextView>(R.id.latex06).apply {
            val drawable = resources.getDrawable(R.drawable.icon_bulb)
            val msg = SpannableHelper("mmmm")
                .appendImage(drawable, 40, 40)
                .build()
            text = msg
        }
        
        // 支持正则替换多个图片
        findViewById<TextView>(R.id.latex10).apply {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_bulb)
            val msg = SpannableHelper("mmmm")
                .setContent("__看时间内卡死看上的空间啊科技空间大__nnjknkkkjjk_加数据看接口","_",this@LatexActivity,bitmap)
                .build()
            text = msg
        }
        
        
```



